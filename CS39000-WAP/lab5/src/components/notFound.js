import React from 'react';
import { Link } from 'react-router-dom';

import "bootstrap/dist/css/bootstrap.min.css";
import { Navbar, Nav, Form, FormControl, Button, Card } from "react-bootstrap";
import "../index.css";
import { searchCall } from "../async.js";
import { saveHistory } from "../redux_components/actions";
import { connect } from "react-redux";

class NotFound extends React.Component {
    render() {
        return (
            <div id="mainContent">
                <center><Link to="/">ERROR 404, GO BACK TO HOME PAGE</Link></center>
            </div>
        );
    }
}

export default NotFound;