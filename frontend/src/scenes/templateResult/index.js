import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../services/Generator";
import {withRouter} from 'react-router-dom';

class TemplateResult extends Component {
    constructor(props) {
        super(props);

        this.state = {
            viewData: {
                title: "",
                description: "",
                example: "",
            },
            apiData: {
                generator: null,
            },
            data: {}
        };
    }

    componentDidMount() {
        this.update()
    }

    update = () => {
        this.loadGenerator(this.props.match.params.id)
    }

    loadGenerator = (id) => {
        let self = this
        GeneratorApi.findById(id)
            .then((response) => {
                response.json().then(result => {
                    let apiData = this.state.apiData
                    apiData.generator = result.result
                    let viewData = this.state.viewData
                    viewData.title = apiData.generator.title
                    viewData.description = apiData.generator.description
                    viewData.example = apiData.generator.example

                    self.setState({
                        apiData: apiData,
                        viewData: viewData
                    })
                })
            })
    }

    onChangeInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;

        let viewData = this.state.viewData;
        viewData[name] = value
        this.setState({viewData: viewData})
    }

    generate = () => {
        let self = this
        GeneratorApi.generate(
            this.state.data.generator.id,
            this.state.viewData.example
        ).then((result) => {
            result.json().then((responseJson) => {
                debugger
                const json = responseJson.result
            })
        })
    }

    onSubmit = (e) => {
        e.preventDefault();
        this.generate()
        return false;
    }

    render() {
        return (
            <section>
                <div className="container">
                    <h1>{this.state.viewData.title}</h1>
                    <p>{this.state.viewData.description}</p>

                    <form onSubmit={this.onSubmit}>
                        <div className="row">
                            <div className="col">
                                <div className="form-group">
                                    <label htmlFor="txtExample">JSON with your data</label>
                                    <textarea id="txtExample"
                                              className="form-control"
                                              name={'example'}
                                              onChange={this.onChangeInput}
                                              value={this.state.viewData.example}/>
                                </div>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col">
                                <button type="submit" className="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        );
    }
}


export default withRouter(TemplateResult);
