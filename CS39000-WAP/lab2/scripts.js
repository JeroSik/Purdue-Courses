let allNoteCards = [];
let hashID = 0;

// User action when submit is pressed
let submit = document.getElementById("submitButton");
submit.addEventListener("click", () => {
    // Get title and text values from input field
    let title = document.getElementById("titleInput").value;
    let text = document.getElementById("inputArea").value;

    // Make sure note card at least has title
    if (title) {
        // Create new note card
        createNewNoteCard(title, text);

        // Clear input fields
        document.getElementById("titleInput").value = "";
        document.getElementById("inputArea").value = "";
    }
})

// User action when delete is pressed
let submit = document.getElementById("submitButton");
submit.addEventListener("click", () => {
    // Get title and text values from input field
    let title = document.getElementById("titleInput").value;
    let text = document.getElementById("inputArea").value;

    // Make sure note card at least has title
    if (title) {
        // Create new note card
        createNewNoteCard(title, text);

        // Clear input fields
        document.getElementById("titleInput").value = "";
        document.getElementById("inputArea").value = "";
    }
})

function createNewNoteCard(newTitle, newText) {
    let noteCardHTML = createNoteCardHTML(newTitle, newText);
    noteCardHTML.addEventListener("click", () => {
        // noteCardHTML.className = "card border border-danger";
    })

    let noteCard = {
        title: newTitle,
        text: newText,
        id: hashID,
    }

    let noteCardList = document.getElementById("notecardList");
    noteCardList.appendChild(noteCardHTML);
    
    hashID += 1;
}

function createNoteCardHTML(newTitle, newText) {
    let cardTitle = document.createElement("h5");
    cardTitle.className = "card-title";
    let title = document.createTextNode(newTitle);
    cardTitle.appendChild(title);

    let cardText = document.createElement("p");
    cardText.className = "card-text";
    let text = document.createTextNode(newText);
    cardText.appendChild(text);

    let cardBody = document.createElement("div");
    cardBody.className = "card-body";
    cardBody.appendChild(cardTitle);
    cardBody.appendChild(cardText);

    let card = document.createElement("div");
    card.className = "card";
    card.appendChild(cardBody);

    return card;
}

// let list = document.getElementById("notecardList");
// list.appendChild(createNewNoteCard("asdf", "asgag"));

// (function () {
//     /* Add your code here */
// }())