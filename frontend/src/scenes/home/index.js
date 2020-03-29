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
import LeftMenuComponent from "../../components/LeftMenu";

class HomeScene extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {},
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
                <div className="container-fluid">
                    <div className="row">
                        <LeftMenuComponent/>

                        <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                            <section>
                                <div className="container-fluid">
                                    <div className={'row'}>
                                        <div className={'col'}>
                                            <h1>RGenerator</h1>
                                            <p>Dont copy - generate it</p>
                                        </div>
                                    </div>
                                </div>
                            </section>

                        </main>
                    </div>
                </div>
            </section>
        );
    }
}


export default HomeScene;
