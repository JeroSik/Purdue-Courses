// Global variables
let allNoteCards = [];
let selectedNoteCards = new Set([]);
let hashID = 0;

// User action when submit is pressed
document.getElementById("submitButton").addEventListener("click", () => {
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
        noteCardList.appendChild(createNoteCardHTML(card.title, card.text, card.id));
    })
};

// Helper function to check if card has search key
function checkSearch(word) {
    return function(card) {
        return card.title.includes(word);
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

    // Add note card to list of all cards
    allNoteCards.push(noteCard);

    // Append note card to HTML
    document.getElementById("notecardList").appendChild(createNoteCardHTML(newTitle, newText, hashID));
    
    // Increment hash ID
    hashID += 1;
}

// Helper function to create HTML of note card
function createNoteCardHTML(newTitle, newText, newID) {
    // Create title element
    let cardTitle = document.createElement("h5");
    cardTitle.className = "card-title";
    let title = document.createTextNode(newTitle);
    cardTitle.appendChild(title);

    // Create text element
    let cardText = document.createElement("p");
    cardText.className = "card-text";
    let text = document.createTextNode(newText);
    cardText.appendChild(text);

    // Create body to contain title and text
    let cardBody = document.createElement("div");
    cardBody.className = "card-body";
    cardBody.appendChild(cardTitle);
    cardBody.appendChild(cardText);

    // Create card div
    let card = document.createElement("div");
    card.className = "card";
    card.setAttribute("id", newID);
    card.appendChild(cardBody);

    // Add event listener for selection
    card.addEventListener("click", () => {
        if (card.getAttribute("class") == "card") {
            card.className = "card border border-danger";
            selectedNoteCards.add(card.getAttribute("id"));
        } else {
            card.className = "card";
            selectedNoteCards.delete(card.getAttribute("id"));
        }
    })

    return card;
}