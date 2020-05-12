import {makeStyles} from "@material-ui/core/styles";

const CardLinkStyles = makeStyles((theme) => ({

    container: {
        padding: '8px 16px 8px 16px',
        minHeight: '150px'
    },

    title: {
        fontWeight: 500,
        fontSize: '16px',
        lineHeight: '20px',
        letterSpacing: '0.15px',
        color: '#000000'
    },

    description: {
        fontSize: '14px',
        lineHeight: '18px',
        color: '#000000'
    }

}));

export default CardLinkStyles;