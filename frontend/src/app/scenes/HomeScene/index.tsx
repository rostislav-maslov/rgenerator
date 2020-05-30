import React, {useEffect} from 'react';

import Props from "./Props";
import HeaderComponent from '../../components/mainPage/HeaderComponent'
import HowItWorksComponent from '../../components/mainPage/HowItWorksComponent'
import Grid from '@material-ui/core/Grid';
import HomeStyle from "./HomeStyle";


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
                        <HowItWorksComponent  video={'https://www.youtube.com/embed/5qap5aO4i9A'}/><br/>
                    </Grid>
                </Grid>
            </section>
        </section>
    );
}

export default HomeScene;
