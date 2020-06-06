import React, {Component, useEffect, useState} from 'react';

import './style.css';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";


import GeneratorApi from "../../../gateways/services/Generator";
import LeftMenuComponent from "../../components/explore/LeftMenuComponent";
import {generateLeftMenuProps} from "../../components/explore/LeftMenuComponent/LeftMenuProps";
import Breadcrumbs from "../../components/base/BreadcrumbsComponent/BreadcrumbsComponent";
import ExploreProps from "./ExploreProps";
import Grid from '@material-ui/core/Grid';
import ExploreStyles from "./ExploreStyles";
import GeneratorItemComponent from "../../components/explore/GeneratorItemComponent";
import GeneratorItemProps from "../../components/explore/GeneratorItemComponent/GeneratorItemProps";
import Button from '@material-ui/core/Button';
import AddIcon from '@material-ui/icons/Add'


const ExploreScene: React.FC<ExploreProps> = (props: ExploreProps) => {

    window.scrollTo(0, 0)
    const classes = ExploreStyles()
    const [generatorList, setGeneratorList] = useState([])

    useEffect(() => {
        GeneratorApi.list()
            .then((result) => {
                setGeneratorList(result.result.items)
            })
    }, generatorList)

    return (
        <section>
            <Breadcrumbs links={[
                {title: 'Home', url: '/'},
                {title: 'Explore', url: '/explore', active: true},
            ]}/>

            <section className={classes.container}>
                <Grid container spacing={3}
                      direction="row"
                      justify="flex-start"
                      alignItems="flex-start"
                >
                    <Grid item xs={12} md={4} lg={3}>
                        <LeftMenuComponent {...generateLeftMenuProps('explore', null)}/>
                    </Grid>
                    <Grid item xs={12} md={8} lg={9}>
                        {generatorList.map((generator: GeneratorItemProps, idx) => {
                            return (
                                <GeneratorItemComponent {...generator}/>)
                        })}

                        {generatorList.length === 0 ? (
                            <div style={{textAlign: 'center'}}>
                                <Button href={'/generator/create'} color="primary"
                                        startIcon={<AddIcon/>} variant="contained">
                                    CREATE
                                </Button>
                            </div>
                        ) : false}

                    </Grid>
                </Grid>
            </section>
        </section>
    )
}

export default ExploreScene;