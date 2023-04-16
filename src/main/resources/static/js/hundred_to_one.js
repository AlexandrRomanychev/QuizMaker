function saveNewQuestion() {
    let names = document.getElementsByName('answer');
    let question = document.getElementById('questionText').value

    let answerList = [];
    for(let key=0; key < names.length; key++)  {
        answerList.push({text: names[key].value});
    }
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': '/saveNewQuestion',
        'data': JSON.stringify({
            text: question,
            answerList: answerList
        }),
        'dataType': 'json'
    }).done(function (data) {
        // TODO обработку ошибки
        window.location.replace('/')
    });
}

function addNewAnswer() {
    addNewAnswerWithText('')
}

function addNewAnswerWithText(answerText) {
    let answerList = document.getElementById('answerList');
    let answerId = Date.now()
    answerList.insertAdjacentHTML('beforeend','<div class="form-group" name="answerBlock" id="' + answerId + '"><label>Ответ: </label><input type="text" name="answer" class="form-control" value="' + answerText + '"><br/>' +
        '<button type="button" onclick="deleteAnswer('+ answerId +')">Удалить ответ</button>' +
        '<button type="button" onclick="moveUp('+ answerId +')">Вверх</button>' +
        '<button type="button" onclick="moveDown(' + answerId + ')">Вниз</button></div>')
}

function deleteAnswer(answerId) {
    let answer = document.getElementById(answerId);
    answer.parentNode.removeChild(answer)
}

/**
 * Поднять ответ на строчку выше
 * @param answerId
 */
function moveUp(answerId) {
    moveItemInList((key) => key - 1, (array) => 0, answerId)
}

/**
 * Опустить ответ на строчку ниже
 * @param answerId
 */
function moveDown(answerId) {
    moveItemInList((key) => key + 1, (array) => array.length - 1, answerId)
}

/**
 * Перемещение ответа вниз/вверх
 * @param nextItemNumberFunction
 * @param borderNumber
 * @param itemId
 */
function moveItemInList(nextItemNumberFunction, borderNumber, itemId) {
    let answerList = document.getElementsByName('answerBlock');
    for(let key=0; key < answerList.length; key++)  {
        if (answerList[key].id === itemId.toString() && key !== borderNumber(answerList)) {
            let item = answerList[nextItemNumberFunction(key)].querySelector("input[name='answer']").value;
            answerList[nextItemNumberFunction(key)].querySelector("input[name='answer']").value = answerList[key].querySelector("input[name='answer']").value;
            answerList[key].querySelector("input[name='answer']").value = item;
        }
    }
}