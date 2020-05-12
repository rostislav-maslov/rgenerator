import {makeStyles} from "@material-ui/core/styles";


const HowItWorksStyles = makeStyles((theme) => ({
    content: {
        padding: '24px',
        minHeight: '40vh'
    },

    title: {
        fontWeight: 'bold',
        fontSize: '48px',
        lineHeight: '48px',
        letterSpacing: '0.15px',
        color: '#000000',
        marginBottom: '8px'
    },

    videoFrame: {
        width: '100%',
        height: '50vh',
        border: 'none'
    }
}));

export default HowItWorksStyles;
