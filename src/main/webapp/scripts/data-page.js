const toggleEvaluationStates = {}

function toggleEvaluation(evaluationId) {
    if(toggleEvaluationStates[`e${evaluationId}`] !== undefined) {
        toggleEvaluationStates[`e${evaluationId}`] = !toggleEvaluationStates[`e${evaluationId}`]
    } else {
        toggleEvaluationStates[`e${evaluationId}`] = true
    }

    if(!toggleEvaluationStates[`e${evaluationId}`]) {
        document.getElementById(`toggle-btn-evaluation-${evaluationId}`).innerHTML = `<img src="/icons/chevron-down.svg" alt="chevron-down" class="w-8"/>`;
        document.getElementById(`evaluation-data-${evaluationId}`).classList.add("hidden");
    } else {
        document.getElementById(`toggle-btn-evaluation-${evaluationId}`).innerHTML = `<img src="/icons/chevron-up.svg" alt="chevron-down" class="w-8"/>`;
        document.getElementById(`evaluation-data-${evaluationId}`).classList.remove("hidden");
    }
}