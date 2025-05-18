let __name;
let __startTime;
let __endTime;
let __records;

// initial
function init(data, anchors) {
    __name = data.name;
    __startTime = data.startTime;
    __endTime = data.endTime;
    __records = [];
}

// accessors
function getName() {
    return name;
}

function getStartTime() {
    return __startTime;
}

function getEndTime() {
    return __endTime;
}

function add(level) {
    __records.push(level);
}

// controllers
const __ids= {
    container: "cl2n-container",
    record: "cl2n-record-",
    add: "cl2n-add-",
    remove: "cl2n-remove-"
}

function addRow(button) {
    const level = parseIdValue(button, __ids.add);
    const container = document.getElementById(__ids.container);

    const id = crypto.randomUUID();
    const html = `
        <div id="${__ids.record + id}">
            <p>${level}</p>
            <button id="${__ids.remove + id}" onclick="removeRow(this)">Remove</button>
        </div>
    `;
    container.insertAdjacentHTML("beforeend", html);
}

function removeRow(button) {
    const index = parseIdValue(button, __ids.remove);
    document.getElementById(__ids.record + index).remove();
}

// utils
function parseIdValue(button, prefix) {
    if (!button.id.startsWith(prefix)) return null;
    return button.id.replace(prefix, "");
}