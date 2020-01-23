import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
  } from "react-router-dom";
import { render } from "react-dom";
import "./index.css";
import Routing from "./Routing";
import * as serviceWorker from "./serviceWorker";
import store from "./redux_components/store";

render(<Routing store={store} />, document.getElementById("root"));

serviceWorker.unregister();
