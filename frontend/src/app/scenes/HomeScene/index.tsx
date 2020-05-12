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
                    <Grid item xs={12} spacing={5}>
                        <HowItWorksComponent/>
                    </Grid>
                </Grid>
            </section>
        </section>
    );
}

export default HomeScene;
