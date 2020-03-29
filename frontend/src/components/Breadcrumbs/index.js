import React, {Component} from 'react';
import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

class Breadcrumbs extends Component {
    render() {
        return (
            <nav aria-label="breadcrumb">
                <ol className="breadcrumb">
                    {this.props.links.map((link) => {
                        return (<li className="breadcrumb-item"><Link to={link.url}>{link.title}</Link></li>)
                    })}
                </ol>
            </nav>
        );
    }
}

export default Breadcrumbs;
