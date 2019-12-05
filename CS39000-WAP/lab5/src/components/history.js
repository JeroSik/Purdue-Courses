import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Navbar, Nav, Card } from "react-bootstrap";
import "../index.css";
import { connect } from "react-redux";
import { Link } from "react-router-dom";

/**
 * starwars character object:
 * {
 *    "name": "Luke Skywalker",
 *    "birth_year": "19BBY",
 *    "gender": "male",
 *    "height": "172",
 *    "mass": "77",
 *    "hair_color": "blonde",
 *    "eye_color": "blue"
 * }
 */

class History extends React.Component {
  render() {
    /**
     * TODO: Uncomment the code below once you set up your redux store
     * and import the "history" state from redux as a prop.
     */
    /*
    const { history } = this.props;
    const historyArray = history.map((obj, index) => {
      const {
        name,
        birth_year,
        gender,
        height,
        mass,
        hair_color,
        eye_color
      } = obj;
      return (
        <Card key={index} bg="light" id="searchCard">
          <Card.Header>{`Search Result (${index + 1})`}</Card.Header>
          <Card.Body>
            <Card.Title id="cardName">{name}</Card.Title>
            <Card.Text id="cardText">
              Birth Year: {birth_year} <br />
              Gender: {gender} <br />
              Height: {height} <br />
              Weight: {mass} <br />
              Hair Color: {hair_color} <br />
              Eye Color: {eye_color} <br />
            </Card.Text>
          </Card.Body>
        </Card>
      );
    });
    */

    return (
      <div>
        {/* <Navbar bg="light" expand="lg">
          <Navbar.Brand href="/">Lab 5: Extra Credit</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              <Link to="/search">Search&nbsp;</Link>
              <Link to="/history">History</Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar> */}
        <div id="mainContent">
          <h1>Last Two Search History</h1>
          {/**
           * TODO: Uncomment below accordingly
           * historyArray
           * */}
        </div>
      </div>
    );
  }
}
/**
 * TODO: create a mapStateToProps function and return the necessary state object.
 * Then modify line 85 to use connect() with mapStateToProps while exporting History.
 */
export default History;
