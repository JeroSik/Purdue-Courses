// let allNoteCards = new Map();
let allNoteCards = [];
let selectedNoteCards = new Set([]);
let hashID = 0;

// User action when submit is pressed
let submitPressed = document.getElementById("submitButton");
submitPressed.addEventListener("click", () => {
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
let deletePressed = document.getElementById("deleteButton");
deletePressed.addEventListener("click", () => {
    // Go through all selected cards
    selectedNoteCards.forEach(function(id) {
        // Go through all current cards
        for (let i = 0; i < allNoteCards.length; i++){
            // Remove card fix IDs match
            if (allNoteCards[i].id == id) {
                allNoteCards.splice(i, 1); 
            }
        }

        // Remove HTML for card
        document.getElementById(id).remove();
    });

    // Reset selected cards
    selectedNoteCards = new Set([]);
})

// User action when searching
var searchTyped = document.getElementById('searchBar');
searchTyped.onkeyup = function() {
    // Find all cards that matched search query
    let matchedCards = allNoteCards.filter(checkSearch(this.value));

    // Remove all current displayed cards
    let noteCardList = document.getElementById("notecardList");
    while (noteCardList.firstChild) {
        noteCardList.removeChild(noteCardList.firstChild);
    }

    // Display all cards that matched
    matchedCards.forEach(function(card) {
        let displayCard = createNoteCardHTML(card.title, card.text, card.id)
        displayCard.addEventListener("click", () => {
            if (displayCard.getAttribute("class") == "card") {
                displayCard.className = "card border border-danger";
                selectedNoteCards.add(displayCard.getAttribute("id"));
            } else {
                displayCard.className = "card";
                selectedNoteCards.delete(displayCard.getAttribute("id"));
            }
        })

        noteCardList.appendChild(displayCard);
    })
};

// Helper function to check if card has search key
function checkSearch(word) {
    return function(card) {
        return (card.title.includes(word) || card.text.includes(word));
    }
}

// Helper function to create new note card
function createNewNoteCard(newTitle, newText) {
    // Create JSON for note card
    let noteCard = {
        id: hashID,
        title: newTitle,
        text: newText
    }

    // Create HTML for note card
    let noteCardHTML = createNoteCardHTML(newTitle, newText, hashID);

    // Add event listener for selection
    noteCardHTML.addEventListener("click", () => {
        if (noteCardHTML.getAttribute("class") == "card") {
            noteCardHTML.className = "card border border-danger";
            selectedNoteCards.add(noteCardHTML.getAttribute("id"));
        } else {
            noteCardHTML.className = "card";
            selectedNoteCards.delete(noteCardHTML.getAttribute("id"));
        }
    })

    // Add note card to list of all cards
    allNoteCards.push(noteCard);

    // Append note card to HTML
    let noteCardList = document.getElementById("notecardList");
    noteCardList.appendChild(noteCardHTML);
    
    // Increment hash ID
    hashID += 1;
}

// Helper function to create HTML of note card
function createNoteCardHTML(newTitle, newText, newID) {
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
    card.setAttribute("id", newID);
    card.appendChild(cardBody);

    return card;
}