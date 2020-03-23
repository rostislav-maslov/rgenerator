import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../services/Generator";

class HomeScene extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {
                catalogs: [],
            },
            apiData: {
                catalogsResponse: [],
                productsResponse: []
            },
            data: {
                productsInBasket: [],
            }
        };
    }

    componentDidMount() {
        this.update()
    }

    update = () => {

    }

    onChange = (e) => {
        debugger;
    }

    render() {
        return (
            <section>
                <div className="container">
                    <form>
                        <div className="row">
                            <div className="col">

                                <input type="file" webkitdirectory mozdirectory directory onChange={this.onChange}/>

                            </div>
                        </div>
                    </form>
                </div>
            </section>
        );
    }
}


export default HomeScene;
