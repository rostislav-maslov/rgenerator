import React from 'react';
import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

function Footer() {
    return (
        <div className={'container-fluid footer-custom'}>
            <div className={'row'}>
                <div className={'col text-center footer-custom-text'}>
                    © 2020 maslov.tech & FoodTech Lab
                </div>
            </div>
        </div>
    );
}
export default Footer;
