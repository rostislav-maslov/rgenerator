import { makeStyles } from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({

    appBar: {
        backgroundColor: 'white',
        color: '#000000'
    },

    appBarLogo: {
        fontSize: '32px',
        fontWeight: 'bold'
    },

    link: {
        color: '#000000',
        fontWeight: 'bold',
        fontSize: '16px',
        lineHeight: '24px',
        letterSpacing: '0.15px',
        marginTop: '6px',
        textDecoration: 'none',
        marginLeft: '24px'
    },

    linkFirst: {
        color: '#000000',
        fontWeight: 'bold',
        fontSize: '16px',
        lineHeight: '24px',
        letterSpacing: '0.15px',
        marginTop: '6px',
        marginLeft: '48px',
        textDecoration: 'none',
    },

    buttons: {
        textDecoration: 'none',
    },

    rightToolbar: {
        marginLeft: 'auto',
        marginRight: 0,
    },
}));

export default useStyles;