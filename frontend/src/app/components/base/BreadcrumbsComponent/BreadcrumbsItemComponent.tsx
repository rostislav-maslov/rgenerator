import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";
import BreadcrumbsItemStyles from "./BreadcrumbsItemStyles";
import BreadcrumbsItemProps from "./BreadcrumbsItemProps";

const BreadcrumbsItemComponent:React.FC<BreadcrumbsItemProps> = (props: BreadcrumbsItemProps) => {
    const classes = BreadcrumbsItemStyles()

    return (
        <Link className={props.active == true? classes.linkActive : classes.link} to={props.url}>{props.title}</Link>
    );
}

export default BreadcrumbsItemComponent;