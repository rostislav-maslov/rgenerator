import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../services/Generator";

class ListScene extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {},
            apiData: {
                list: [],
            },
            data: {}
        };
    }

    componentDidMount() {
        this.update()
    }

    update = () => {
        let self = this
        GeneratorApi.list()
            .then((response) => {
                response.json().then(result => {
                    let apiData = this.state.apiData
                    apiData.list = result.items
                    let viewData = this.state.viewData

                    self.setState({
                        apiData: apiData,
                        viewData: viewData
                    })
                })
            })
    }


    render() {
        return (
            <section>
                <div className="container">
                    <div className={'row'}>
                        <div className={'col'}>
                            <br/>
                            <h1>Templates</h1>
                            <br/>
                            <br/>
                        </div>
                    </div>
                    <div className={'row'}>
                        <div className={'col'}>
                            <div className="list-group">
                                {this.state.apiData.list.map((generator, idx) => {
                                    return (
                                        <Link key={idx} to={'/template-result/' + generator.id}
                                              className="list-group-item list-group-item-action flex-column align-items-start">

                                            <div className="d-flex w-100 justify-content-between">
                                                <h5 className="mb-1">{generator.title}</h5>
                                                <small className="text-muted">3 days ago</small>
                                            </div>
                                            <p className="mb-1">{generator.description}</p>
                                            <small className="text-muted"></small>
                                        </Link>)
                                })}

                                <br/><br/><br/>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}


export default ListScene;
