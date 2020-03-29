import React, {Component} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import GeneratorApi from "../../services/Generator";
import LeftMenuComponent from "../../components/LeftMenu";
import Breadcrumbs from "../../components/Breadcrumbs";

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
        window.scrollTo(0,0)
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
                <div className="container-fluid">
                    <div className="row">
                        <LeftMenuComponent  activeLink={'list'}/>

                        <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                            <section>
                                <div className={'container-fluid'}>
                                    <div className={'row'}>
                                        <div className={'col-10'}>
                                            <Breadcrumbs links={[
                                                {title:'Home', url:'/'},
                                                {title:'Generators', url:'/generator'},
                                            ]}/>
                                        </div>
                                        <div className={'col-2'}>
                                            <Link to={'/generator/create'}
                                                  style={{
                                                      padding: '.69rem 1rem',
                                                      fontSize: '.875rem'
                                                  }}
                                                  className={'btn btn-primary btn-md float-right'}>
                                                Create new
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            </section>
                            <section>
                                <div className="container-fluid">
                                    <div className={'row'}>
                                        <div className={'col'}>
                                            <h1>RGenerator list</h1>
                                            <br/>
                                            <br/>
                                        </div>
                                    </div>
                                    <div className={'row'}>
                                        <div className={'col'}>
                                            <div className="list-group">
                                                {this.state.apiData.list.map((generator, idx) => {
                                                    return (
                                                        <Link key={idx} to={`/generator/${generator.id}/view`}
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
                        </main>
                    </div>
                </div>
            </section>

        );
    }
}


export default ListScene;
