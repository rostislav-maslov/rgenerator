import React, {useEffect} from 'react';

import Props from "./Props";
import HeaderComponent from '../../components/mainPage/HeaderComponent'
import HowItWorksComponent from '../../components/mainPage/HowItWorksComponent'
import Grid from '@material-ui/core/Grid';
import HomeStyle from "./HomeStyle";
import Paper from '@material-ui/core/Paper';
import { Typography } from '@material-ui/core';


const HomeScene: React.FC<Props> = (props: Props) => {
    useEffect(() => {
        window.scrollTo(0, 0)
    })

    const classes = HomeStyle();

    return (
        <section>
            <HeaderComponent/>
            <section>


                <Grid container spacing={5} className={classes.content}>
                    <Grid item xs={12} md={2} lg={2}></Grid>
                    <Grid item xs={12} md={8} lg={8}>
                        <Paper style={{padding: '24px'}}>
                            <Typography variant={'h3'} >
                                Hello,  <span className={classes.dashed}>{'{{'}mainPage.firstName{"}}"}</span>!
                            </Typography>
                            <br/><br/><br/>
                            <Typography variant={'body1'} >
                                We can help you speed up your work on <span className={classes.dashed}>{'{{'}mainPage.projectName{"}}"}</span>! Here simple steps:
                                <br/><br/>
                                1 - Sign Up on RGenerator<br/>
                                2 - Create your template<br/>
                                3 - Create example of JSON<br/>
                                4 - Use it every time when you need create new files in <span className={classes.dashed}>{'{{'}mainPage.projectName{"}}"}</span><br/>
                                <br/>
                                Enjoy!
                                <br/><br/>
                                PS: Take a look on your example - <a href="https://github.com/rostislav-maslov/rgenerator-template-example">Template example</a>
                            </Typography>
                        </Paper>
                    </Grid>
                </Grid>

            </section>
        </section>
    );
}

export default HomeScene;
