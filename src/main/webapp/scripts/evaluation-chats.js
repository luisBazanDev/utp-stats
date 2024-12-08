function renderOne(type, evaluationId, evaluationData) {

    switch (type) {
        case "pie":
            renderPieData(evaluationId, evaluationData);
            break;
        case "radar":
            renderRadarData(evaluationId, evaluationData);
            break;
        case "bars":
            renderBarsData(evaluationId, evaluationData)
            break;
    }

}

function renderPieData(evaluationId, evaluationData) {
    for(const question of evaluationData.questions) {
        const chart = document.getElementById(`chart-e-${evaluationId}-q-${question.number}`)
        const labels = getLabelsOfN(question.value)
        const dataset = {
            label: 'P'+question.number,
            data: [],
            backgroundColor: getColorList(question.value),
            hoverOffset: 4
        }

        for (let i = 0; i <= question.value; i++) {
            dataset.data[i] = 0;
        }


        for(const studentData of evaluationData.studentsData) {
            const questionPoint = studentData.points[question.number-1];
            dataset.data[Math.round(questionPoint)]++;
        }


        const chartOptions = {
            type: "pie",
            data: {
                labels,
                datasets: [dataset]
            }
        }

        new Chart(chart, chartOptions)
    }
}

function renderRadarData(evaluationId, evaluationData) {
    const dataset = {
        label: evaluationData.data.name,
        data: [],
        fill: true,
        backgroundColor: 'rgba(91, 54, 242, .3)',
        borderColor: 'rgba(91, 54, 242, 1)',
        pointBackgroundColor: '#FF385B',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: '#FF6579'
    }

    const chartOptions = {
        type: "radar",
        data: {
            labels: [],
            datasets: [dataset]
        }
    }

    for (const studentData of evaluationData.studentsData) {
        for (let i = 0; i < studentData.points.length; i++) {
            if(!dataset.data[i]) dataset.data[i] = 0;
            dataset.data[i] += studentData.points[i];
        }
    }

    for (let i = 1; i <= evaluationData.data.questionsCount; i++) {
        chartOptions.data.labels.push(`P${i}`)
    }

    const chart = document.getElementById(`chart-e-${evaluationId}`)


    new Chart(chart, chartOptions)
}

function renderBarsData(evaluationId, evaluationData) {
    const dataset = {
        label: evaluationData.data.name,
        data: [],
        fill: true,
        backgroundColor: getColorList(evaluationData.data.questionsCount)
    }

    const chartOptions = {
        type: "bar",
        data: {
            labels: [],
            datasets: [dataset]
        }
    }

    let maxPoints =0

    for (let i = 0; i < evaluationData.questions.length; i++) {
        maxPoints+=evaluationData.questions[i].value
    }

    for (let i = 0; i <= maxPoints; i++) {
        dataset.data[i] = 0
    }

    chartOptions.data.labels = getLabelsOfN(maxPoints)
    dataset.backgroundColor = getColorList(maxPoints + 1)

    for (const studentData of evaluationData.studentsData) {
        if(!dataset.data[Math.round(studentData.totalPoints)]) dataset.data[Math.round(studentData.totalPoints)] = 0;
        dataset.data[Math.round(studentData.totalPoints)]++;
    }

    const chart = document.getElementById(`chart-e-${evaluationId}`)

    console.log(chartOptions)

    new Chart(chart, chartOptions)
}

function renderAll() {
    data.forEach(x => {
        renderOne(x.data.id)
    })
}

function dataSchemaSet(type, evaluationId, evaluationData) {
    const elem = document.getElementById(`data-zone-${evaluationId}`)

    switch (type) {
        case 'pie':
            let html = ''

            for (const question of evaluationData.questions) {
                html += `<div class="flex flex-col items-center gap-4">
                    <span class="text-utp_purple font-bold text-xl">P${question.number}</span>
                    <canvas id="chart-e-${evaluationId}-q-${question.number}" width="400" height="480"></canvas>
                </div>`
            }

            elem.innerHTML = html;
            break;
        case 'radar':
            elem.innerHTML = `
            <div class="w-1/3 flex justify-center">
                <canvas id="chart-e-${evaluationId}">
                </canvas>
            </div>
            `
            break;
        case 'bars':
            elem.innerHTML = `
            <div class="w-2/3 flex justify-center">
                <canvas id="chart-e-${evaluationId}">
                </canvas>
            </div>
            `
            break;
    }

}

function changeChart(type, evaluationId) {
    const classListActive = "flex-1 bg-utp_purple text-white font-bold py-2"
    const classListDefault = "flex-1 border border-utp_purple text-utp_purple py-2"

    for (const typeComparator of ['pie', 'radar', 'bars']) {
        if (type === typeComparator) {
            document.getElementById(`btn-${typeComparator}-${evaluationId}`).className = classListActive;
        } else {
            document.getElementById(`btn-${typeComparator}-${evaluationId}`).className = classListDefault;
        }
    }

    const evaluationData = data.find(x => x.data.id === evaluationId);

    dataSchemaSet(type, evaluationId, evaluationData);

    renderOne(type, evaluationId, evaluationData);
}

function getListOfN(maximum) {
    const list = []
    for (let i = 1; i <= maximum; i++) {
        list.push(i);
    }

    return list;
}

function getLabelsOfN(maximum) {
    const list = []
    for (let i = 0; i <= maximum; i++) {
        list.push(`${i}pts`);
    }

    return list;
}

function getColorList(maximum) {
    const colors = [
        "#FF5733", "#33FF57", "#3357FF", "#F1C40F", "#9B59B6",
        "#2ECC71", "#1ABC9C", "#E74C3C", "#2980B9", "#8E44AD",
        "#3498DB", "#E67E22", "#E91E63", "#3F51B5", "#00BCD4",
        "#009688", "#FF9800", "#CDDC39", "#795548", "#607D8B"
    ];

    const result = [];
    for (let i = 0; i < maximum; i++) {
        result.push(colors[i % colors.length]);
    }

    return result;
}

renderAll()