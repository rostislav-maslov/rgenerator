import React from 'react';
import logo from '../logo.svg';
import './App.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

import Home from "../scenes/home";
import Basket from "../scenes/templateResult";
import Order from "../scenes/list";
import Header from "../components/Header";
import Footer from "../components/Footer";

function App() {
    return (
        <Router>
            <Header/>
            <div className={'content'}>

                <Switch>
                    <Route path="/basket">
                        <Basket/>
                    </Route>
                    <Route path="/order">
                        <Order/>
                    </Route>
                    <Route path="/">
                        <Home/>
                    </Route>
                </Switch>

            </div>
            <Footer/>
        </Router>
    );
}


export default App;
