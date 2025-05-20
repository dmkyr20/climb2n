let __name;
let __startTime;
let __records;

// initial
function init(data) {
    __name = data.name;
    __startTime = data.startTime;
    __records = data.records;
}

// controllers
const __keys = [
    "ONE", "TWO",
    "THREE", "FOUR",
    "FIVE", "SIX",
    "SEVEN"
];
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

function collectLevelCounts() {
    const container = document.getElementById(__ids.container);
    const records = container.children;

    for (const record of records) {
        const levelText = record.querySelector("p")?.textContent?.trim();
        const level = parseInt(levelText, 10);
        const index = level - 1;

        console.log("temp: " + level);
        console.log("index: " + index);
        console.log("key: " + __keys[index]);

        __records[__keys[index]] = (__records[index] || 0) + 1;
    }
}

function submitTraining() {
    collectLevelCounts()
    const trainingData = {
        name: __name,
        startTime: __startTime,
        records: __records
    };

    const csrfToken = document.querySelector('meta[name="csrf-token"]')?.content;
    const csrfHeader = document.querySelector('meta[name="csrf-header"]')?.content;

    fetch("/stop", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(trainingData)
    })
        .then(res => {
            if (res.ok) {
                alert("Training submitted successfully!");
            } else {
                alert("Failed to submit training.");
            }
        })
        .catch(err => {
            console.error("Submit error:", err);
            alert("An error occurred during submission.");
        });
}
