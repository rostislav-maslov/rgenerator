import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";

import LeftMenuProps from "./LeftMenuProps";
import Paper from '@material-ui/core/Paper';
import LeftMenuGroupProps from "./LeftMenuGroupProps";
import LeftMenuStyles from "./LeftMenuStyles";
import LeftMenuItemProps from "./LeftMenuItemProps";
import LeftMenuItemComponent from "./LeftMenuItemComponent";

const LeftMenuComponent: React.FC<LeftMenuProps> = (props: LeftMenuProps) => {
    const classes = LeftMenuStyles()

    return (
        <Paper className={classes.container}>
            {props.groups.map((group: LeftMenuGroupProps) => {
                return (
                    <div className={classes.group}>
                        {group.items.map((item: LeftMenuItemProps) => {
                            return (
                                <LeftMenuItemComponent {...item}/>
                            );
                        })}
                    </div>
                )
            })}
        </Paper>
    );
}


export default LeftMenuComponent;
