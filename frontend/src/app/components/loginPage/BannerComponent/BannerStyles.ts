import {makeStyles} from "@material-ui/core/styles";


const BannerStyles = makeStyles((theme) => ({
    container: {
        background: 'radial-gradient( circle farthest-corner at 50.1% 52.3%,  rgba(255,231,98,1) 58.2%, rgba(251,212,0,1) 90.1% )',
        minHeight: 'calc( 100vh - 64px - 56px )',
        paddingTop: '40%',
        paddingLeft: ' 20%'
    },

    title: {
        fontWeight: 'bold',
        fontSize: '48px',
        color: '#FFFFFF',
        lineHeight: '48px'
    },

    subTitle: {
        fontWeight: 500,
        fontSize: '14px',
        color: '#FFFFFF'

    }


}));
export default BannerStyles;