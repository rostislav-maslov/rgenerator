import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";

import CardLinkProps from "./CardLinkProps";
import Paper from '@material-ui/core/Paper';
import CardLinkStyles from "./CardLinkStyles";

const CardLinkComponent: React.FC<CardLinkProps> = (props: CardLinkProps) => {
    const classes = CardLinkStyles()

    return (
        <Paper className={classes.container}>
            <div className="card" style={{cursor: 'pointer'}}  >
                <div className="card-body">
                    <h5 className="card-title">{props.title}</h5>
                    <p className="card-text">{props.description}</p>
                </div>
            </div>
        </Paper>
    );
}


export default CardLinkComponent;
