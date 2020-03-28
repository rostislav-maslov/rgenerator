import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import ReactJson from 'react-json-view'

import GeneratorApi from "../../services/Generator";

class HomeScene extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {

            },
            apiData: {
                catalogsResponse: [],
                productsResponse: []
            },
            data: {
                filesToUpload: [],
                generator: null
            }
        };
    }

    componentDidMount() {
        this.update()
    }

    update = () => {

    }

    render() {
        return (
            <section>
                <div className="container">
                    <h1>RGenerator</h1>
                    <p>Dont copy - generate it</p>



                </div>
            </section>
        );
    }
}


export default HomeScene;
