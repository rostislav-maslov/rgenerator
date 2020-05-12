import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";
import Breadcrumbs from '@material-ui/core/Breadcrumbs'
import BreadcrumbsProps from "./BreadcrumbsProps";
import BreadcrumbsStyles from "./BreadcrumbsStyles";
import BreadcrumbsItemComponent from "./BreadcrumbsItemComponent";
import BreadcrumbsItemProps from "./BreadcrumbsItemProps";

const BreadcrumbsComponent: React.FC<BreadcrumbsProps> = (props: BreadcrumbsProps) => {
    const classes = BreadcrumbsStyles()
    return (
        <section className={classes.container}>
            <Breadcrumbs separator="›" aria-label="breadcrumb" >
                {props.links.map((link: BreadcrumbsItemProps) => {
                    return (
                        <BreadcrumbsItemComponent {...link}/>
                    )
                })}
            </Breadcrumbs>
        </section>
    );
}

export default BreadcrumbsComponent
