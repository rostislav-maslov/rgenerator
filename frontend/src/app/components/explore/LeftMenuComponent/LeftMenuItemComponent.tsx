import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";

import LeftMenuItemProps from "./LeftMenuItemProps";
import LeftMenuItemStyles from "./LeftMenuItemStyles";
import AddIcon from '@material-ui/icons/Add';

const LeftMenuItemComponent: React.FC<LeftMenuItemProps> = (props: LeftMenuItemProps) => {

    const classes = LeftMenuItemStyles()

    return (
        <div className={classes.container}>
            <Link to={props.link} className={props.isActive ? `${classes.link} ${classes.active}` : classes.link}>
                {props.title}
            </Link>

            {props.showBadge == true ? <div className={classes.badge}>{props.badge}</div>:false}
            {props.showPlusButton == true ? <div  className={classes.plus} onClick={() => {
                window.location.href = '/generator/create'
            }} style={{cursor:'pointer'}}><AddIcon/></div>:false}
        </div>
    );
}

export default LeftMenuItemComponent;
