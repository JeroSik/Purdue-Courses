import React from "react";
import { render } from "react-dom";
import "./index.css";
import Routing from "./Routing";
import * as serviceWorker from "./serviceWorker";
import store from "./redux_components/store";

render(<Routing store={store} />, document.getElementById("root"));

serviceWorker.unregister();
