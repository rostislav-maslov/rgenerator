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
            viewData: {
            },
            apiData: {
                list: [],
            },
            data: {
            }
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
                            <h1>List</h1>
                        </div>
                    </div>
                    <div className={'row'}>
                        <div className={'col'}>
                            {this.state.apiData.list.map((generator, idx) => {
                                return (<div key={idx}>
                                    <Link to={'/template-result/' + generator.id}> {generator.title}</Link>
                                </div>)
                            })}

                        </div>
                    </div>
                </div>
            </section>
        );
    }
}


export default ListScene;
