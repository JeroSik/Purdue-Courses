import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Navbar, Nav, Form, FormControl, Button, Card } from "react-bootstrap";
import "../index.css";
import { searchCall } from "../async.js";
import { saveHistory } from "../redux_components/actions";
import { connect } from "react-redux";
import { Link } from "react-router-dom";

class Search extends React.Component {
  render() {
    /**
     * TODO: Uncomment line below when redux store is ready to import dispatch method from props.
     */
    // const { saveHistory } = this.props;
    return (
      <div>
        {/* <Navbar bg="light" expand="lg">
          <Navbar.Brand href="/">Lab 5: Extra Credit</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              <Link to="/search">Search</Link>
              <Link to="/history">History</Link>
            </Nav>
          </Navbar.Collapse>
        </Navbar> */}
        <div id="mainContent">
          <h1>Character Search</h1>
          <Form inline id="movieForm">
            <FormControl
              type="text"
              placeholder="Character Name"
              className="mr-sm-2"
              id="characterName"
            />
            <Button
              variant="outline-light"
              onClick={
                /**
                 * Once you set up redux store and obtain saveHistory from this.props,
                 * pass saveHistory as a parameter to searchCall and modify the searchCall method accordingly.
                 */
                () => searchCall()
              }
            >
              Search
            </Button>
          </Form>

          <Card bg="light" id="resultCard">
            <Card.Header>First Search Result</Card.Header>
            <Card.Body>
              <Card.Title id="cardName">
                Please search for a Star Wars character above
              </Card.Title>
              <Card.Text id="cardText"></Card.Text>
            </Card.Body>
          </Card>
        </div>
      </div>
    );
  }
}
/**
 * TODO: create a mapDispatchToProps function and return a JSON object with the necessary dispatch methods.
 * Then modify line 60 to use connect() with mapDispatchToProps while exporting Search.
 */
export default Search;
