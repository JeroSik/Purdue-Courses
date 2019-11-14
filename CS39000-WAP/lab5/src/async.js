import axios from "axios";
/**
 * TODO: Finish implementing the searchCall method by using axios.get to retrieve the data and updating the HTML accordingly.
 * Once the redux store is set up, modify the searchCall function to take in a parameter (saveHistory function) and call it within the method
 * to store the values in the history state in addition to updating the HTML.
 *
 */
export function searchCall() {
  let firstResult;
  console.log("Search Call Made");
  let characterName = document.getElementById("characterName").value;
  console.log("Character Name: " + characterName);

  let requestURL = "https://swapi.co/api/people/?search=" + characterName;

  //These might be useful with some data ;)
  document.getElementById("cardName").innerHTML = "Name";
  document.getElementById("cardText").innerHTML = `
        Birth Year: birth_year <br/>
        Gender: gender <br/>
        Height: height <br/>
        Weight: mass <br/>
        Hair Color: hair_color <br/>
        Eye Color: eye_color <br/>
    `;
}
