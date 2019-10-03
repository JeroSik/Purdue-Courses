import React, { Component } from "react";
import { Button, Table } from "reactstrap";
import Row from "./row.js";
import Tile from "./tile.js";

const rows = 6;
const cols = 7;
const player1 = 1;
const player2 = 2;
const checkElements = arr => /([12]),\1,\1,\1/.test(arr.toString());

class Board extends Component {
  constructor(props) {
    super(props);

    this.state = {
      /*
      Part 1:
      Add appropriate state values
      */
      board: [[]],
      currPlayer: 1,
      gameOver: false,
    };
  }
  
  createBoard() {
    /*
    Part 1:
    Initialize board with null values
    */
    let emptyBoard = new Array(6);
    for (let i = 0; i < emptyBoard.length; i++) {
      emptyBoard[i] = new Array(7);
      emptyBoard[i].fill(null);
    }

    this.setState({board: emptyBoard, currPlayer: 1, gameOver: false});
  }

  placeToken(col) {
    const { gameOver, board, currPlayer } = this.state;
    let newBoard = board;
    if (!gameOver) {
      /*
      Part 3:
      Place the token in the appropriate column, update the current player and update the state accordingly
      */
      for (var i = newBoard.length - 1; i >= 0; i--) {
        if (newBoard[i][col] == null) {
          newBoard[i][col] = currPlayer;
          let newPlayer = (currPlayer == 1) ? 2 : 1;
          this.setState({board: newBoard, currPlayer: newPlayer});
          break;
        }
      }
    }
  }

  checkColumns(table) {
    return table.reduce(
      (hasMatch, column) => hasMatch || checkElements(column),
      false
    );
  }

  checkRows(table) {
    for (let i = 0; i < table[0].length; ++i) {
      let rowArray = table.map(column => column[i]);
      if (checkElements(rowArray)) return true;
    }
    return false;
  }

  checkDiagnols(table) {
    for (let row = 0; row < table.length - 3; row++) {
      for (let col = 0; col < table[row].length - 3; col++) {
        let element = table[row][col];
        if (element == null) {
          continue;
        }
        if (element == table[row + 1][col + 1] && 
            element == table[row + 2][col + 2] && 
            element == table[row + 3][col + 3]) {
          return true;
        }
      }
    }

    for (let row = 0; row < table.length - 3; row++) {
      for (let col = 3; col < table[row].length; col++) {
        let element = table[row][col];
        if (element == null) {
          continue;
        }
        if (element == table[row + 1][col - 1] && 
            element == table[row + 2][col - 2] && 
            element == table[row + 3][col - 3]) {
          return true;
        }
      }
    }

    return false;
  }

  checkTable(table) {
    return this.checkRows(table) || this.checkColumns(table) || this.checkDiagnols(table);
  }

  componentDidMount() {
    this.createBoard();
  }

  componentDidUpdate(prevProps, prevState) {
    const { board, gameOver } = this.state;
    /*
    Part 4:
    End the game if a player wins 
    */
    if (!gameOver) {
      if (this.checkTable(board)) {
        this.setState({gameOver: true});
      }
    }
  }

  render() {
    const { board, currPlayer, gameOver } = this.state;
    const playerColor = gameOver
      ? currPlayer === 1
        ? "yellow"
        : "red"
      : currPlayer === 1
      ? "red"
      : "yellow";
    let connectBoard = board.map((row, index) => <Row key={index} row={row} placeToken={this.placeToken.bind(this)}/>);
    return (
      <React.Fragment>
        <div className="flex justify-center">
          <Button
            color="primary"
            className="btn-block"
            onClick={() => {
              this.createBoard();
            }}
          >
            New Game
          </Button>
        </div>
        <div className="flex justify-center">
          <Table style={{ marginBottom: "0px" }} className="w-30 b--light-blue">
            <thead></thead>
            <tbody>
              {
                /*
                Part 2:
                Map rows in board to individual Row components with the .map function.
                Make sure to pass placeToken function so that the child component can call the function
                */
                connectBoard
              }
            </tbody>
          </Table>
          <h2 className={`flex justify-center w-20 ${playerColor}`}>
            {gameOver
              ? `Player ${currPlayer === 1 ? "2" : "1"} Won!`
              : `Player ${currPlayer}'s Turn!`}
          </h2>
        </div>
      </React.Fragment>
    );
  }
}

export default Board;
