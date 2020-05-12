import {makeStyles} from "@material-ui/core/styles";


const HeaderStyles = makeStyles((theme) => ({
    content: {
        backgroundImage: 'radial-gradient( circle farthest-corner at 10% 20%,  rgba(90,92,106,1) 0%, rgba(32,45,58,1) 81.3% )',
        minHeight: '410px',
    },
    titleDiv: {
        color: 'white',
        maxWidth: '301px',
        fontWeight: 'bold',
        fontSize: '48px',
        lineHeight: '48px',
        letterSpacing: '0.15px',
    },
    subtitleDiv: {
        maxWidth: '464px',
        fontWeight: 500,
        fontSize: '14px',
        lineHeight: '18px',
        color: '#FFFFFF',
        marginTop: '16px'
    }
}));

export default HeaderStyles;
