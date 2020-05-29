import React from 'react';
import {Link} from "react-router-dom";

import useStyles from "./GeneratorItemStyles";
import GeneratorItemProps from "./GeneratorItemProps";
import Paper from '@material-ui/core/Paper';

const GeneratorItemComponent: React.FC<GeneratorItemProps> = (props: GeneratorItemProps) => {
    const classes = useStyles();

    return (
        <Paper className={classes.container} onClick={() => {window.location.href = `/generator/${props.id}/view`;}}>
            <Link key={props.id} to={`/generator/${props.id}/view`} className={classes.title}>
                {props.title}
            </Link>

            <small className={classes.date}>owner: {props.loginOwner}</small>
            {/*<small className={classes.date}>3 days ago</small>*/}
            <p className={classes.description}>{props.description}</p>

        </Paper>
    )
}

export default GeneratorItemComponent;

