import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../services/Generator";
import TemplateResultApi from "../../services/TemplateResult";
import {withRouter} from 'react-router-dom';
import {FILE, FILE_ATTACH} from "../../services/Const";
import ReactJson from 'react-json-view'

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
                templateResult: null
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
                    viewData.example = JSON.parse(apiData.generator.example)
                    viewData.exampleString = apiData.generator.example

                    self.setState({
                        apiData: apiData,
                        viewData: viewData
                    })
                })
            })
    }



    generate = () => {
        let self = this
        TemplateResultApi.create(
            this.state.apiData.generator.id,
            JSON.stringify(this.state.viewData.example)
        ).then((result) => {
            result.json().then((responseJson) => {
                const json = responseJson.result
                let apiData = self.state.apiData
                apiData.templateResult = json
                self.setState({apiData: apiData})
            })
        })
    }

    onSubmit = (e) => {
        e.preventDefault();
        this.generate()
        return false;
    }

    onUpdateJson = (e) => {
        let viewData = this.state.viewData;
        viewData.example = e.updated_src
        viewData.exampleString = JSON.stringify(e.updated_src, null, 4)
        this.setState({viewData:viewData})
    }

    onChangeExample = (e) => {
        try {
            const value = JSON.parse(e.target.value);

            let viewData = this.state.viewData;
            viewData.example = value
            viewData.exampleString = e.target.value
            this.setState({viewData: viewData})
        }catch (ex) {
            let viewData = this.state.viewData;
            viewData.exampleString = e.target.value
            this.setState({viewData: viewData})
        }
    }

    onChangeInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;

        let viewData = this.state.viewData;
        viewData[name] = value
        this.setState({viewData: viewData})
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

                                    <textarea className="form-control" id="txtExample" name={'example'}
                                              onChange={this.onChangeExample}
                                              value={this.state.viewData.exampleString}/>

                                    <ReactJson
                                        src={this.state.viewData.example}
                                        collapsed={false}
                                        enableClipboard={true}
                                        onEdit={this.onUpdateJson}
                                        onAdd={this.onUpdateJson}
                                        onDelete={this.onUpdateJson}
                                    />
                                </div>
                            </div>
                        </div>

                        <div className="row">
                            <div className="col">
                                <button type="submit" className="btn btn-primary">Submit</button>
                            </div>
                        </div>

                        {this.state.apiData.templateResult != null ? (
                            <div className="row">
                                <div className="col">
                                    Результаты:
                                    <a href={FILE_ATTACH(this.state.apiData.templateResult.resultFileId)}
                                       target={'_blank'}>
                                        Ссылка на скачивание файла zip
                                        - {this.state.apiData.templateResult.resultFileId}
                                    </a>
                                </div>
                            </div>
                        ) : false}

                    </form>
                </div>
            </section>
        );
    }
}


export default withRouter(TemplateResult);
