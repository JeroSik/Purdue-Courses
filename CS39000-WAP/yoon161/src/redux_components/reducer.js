import { HistoryRedux } from "./constants";
import { saveHistory } from "./actions";

/**
 * object:
 *  {
      name: "Luke Skywalker",
      birth_year: "19BBY",
      gender: "male",
      height: "172",
      mass: "77",
      hair_color: "blonde",
      eye_color: "blue"
    }
 */

const initialState = {
  /**
   * TODO: generate initial state (preferably an array of JSON objects)
   */
  // object:
  //   {
  //     name: "Luke Skywalker",
  //     birth_year: "19BBY",
  //     gender: "male",
  //     height: "172",
  //     mass: "77",
  //     hair_color: "blonde",
  //     eye_color: "blue"
  //   }
};

export const reducer = (state = initialState, action) => {
  /**
   * TODO: Use a switch case to implement the reducer.
   * Each case will be dependent on the action.type value.
   * This is where you update the history by adding an additional history object to the store.
   * Your switch case should have a default case where you return state.
   */
  switch(action.type) {
    case saveHistory:
      return state + 1;
    default:
      return state;
  } 
};
