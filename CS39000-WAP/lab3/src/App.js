import React, { Component } from "react";
import Board from "./components/board.js";

class App extends Component {
  render() {
    return (
      <div>
        <h1 className="f1 tc">Connect Four</h1>
        <Board />
      </div>
    );
  }
}

export default App;
