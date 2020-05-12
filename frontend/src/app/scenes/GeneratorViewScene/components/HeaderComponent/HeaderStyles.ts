import {makeStyles} from "@material-ui/core/styles";

const HeaderStyles = makeStyles((theme) => ({

    title: {
        fontWeight: 500,
        fontSize: '38px',
        lineHeight: '46px',
        color: '#000000',
        marginTop: '0px',
        marginBottom: '8px'
    },

    description: {
        fontSize: '20px',
        lineHeight: '24px',
        color: '#000000',
        marginTop: '0px',
        marginBottom: '24px'
    },

}));

export default HeaderStyles;