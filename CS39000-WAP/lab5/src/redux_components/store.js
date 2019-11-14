import { createStore } from "redux";
import { reducer } from "./reducer";

/**
 * This is your redux store. You can download redux chrom extension to debug and see your store.
 */

const store = createStore(
  reducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

export default store;
