import axios from "axios";
/**
 * TODO: Finish implementing the searchCall method by using axios.get to retrieve the data and updating the HTML accordingly.
 * Once the redux store is set up, modify the searchCall function to take in a parameter (saveHistory function) and call it within the method
 * to store the values in the history state in addition to updating the HTML.
 *
 */
export function searchCall() {
  const axios = require('axios');

  let firstResult;
  console.log("Search Call Made");
  let characterName = document.getElementById("characterName").value;
  console.log("Character Name: " + characterName);

  let requestURL = "https://swapi.co/api/people/?search=" + characterName;

  axios.get(requestURL)
  .then(function (response) {
    // handle success
    console.log(response.data.results);

    if (response.data.results.length == 0) {
      handleEmptyQuery();
    } else {
      handleSearchQuery(response.data.results[0]);
    }
  })
  .catch(function (error) {
    // handle error
    console.log(error);
  })
  .finally(function () {
    // always executed
  });
}

function handleEmptyQuery() {
  console.log("empty query");
  document.getElementById("cardName").innerHTML = "No charcter found.";
  document.getElementById("cardText").innerHTML = `Try another search!`;
}

function handleSearchQuery(query) {
  document.getElementById("cardName").innerHTML = query.name;
  document.getElementById("cardText").innerHTML = `
      Birth Year: ` + query.birth_year + `<br/>
      Gender: ` + query.gender  + `<br/>
      Height: ` + query.height  + `<br/>
      Weight: ` + query.mass  + `<br/>
      Hair Color: ` + query.hair_color  + `<br/>
      Eye Color: ` + query.eye_color  + `<br/>
  `;
}
