const evaluationChanges = []

function preSaveChange(newChange) {
    let finded = false
    for (let i = 0; i < evaluationChanges.length; i++) {
        const change = evaluationChanges[i]
        if (
            change.evaluationId === newChange.evaluationId &&
            change.studentId === newChange.studentId &&
            change.questionNumber === newChange.questionNumber
        ) {
            evaluationChanges[i] = newChange;
            finded = true;
            break;
        }
    }

    if(!finded) evaluationChanges.push(newChange);
}

function saveEvaluationChanges() {
    if(evaluationChanges.length === 0) {
        alert("Error: No changes");
        return;
    }

    // TODO: Save on backend
}

document.addEventListener("keyup", e => {
    // s##-q##
    const elemId = e.srcElement.id
    if (!elemId) return;

    const studentId = Number.parseInt(elemId.split("-")[0].slice(1))
    const questionNumber = Number.parseInt(elemId.split("-")[1].slice(1))
    const evaluationId = Number.parseInt(elemId.split("-")[2].slice(1))
    const maximum = Number.parseInt(elemId.split("-")[3].slice(1))
    const value = Number.parseFloat(e.srcElement.value);

    if(
        studentId === NaN ||
        questionNumber === NaN ||
        evaluationId === NaN ||
        maximum === NaN ||
        value === NaN
    ) return;

    if (/^\d+(\.\d+)?$/.test(value) && value <= maximum) {
        e.srcElement.style.border = "";
        e.srcElement.style.outline = "";
    } else {
        e.srcElement.style.border = "2px solid red";
        e.srcElement.style.outline = "2px solid red";
        return;
    }

    const data = {
        studentId: studentId,
        questionNumber: questionNumber,
        evaluationId: evaluationId,
        value: value
    }

    preSaveChange(data)

    console.log(evaluationChanges)
})