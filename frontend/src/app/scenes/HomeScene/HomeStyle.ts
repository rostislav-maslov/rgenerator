import {makeStyles} from "@material-ui/core/styles";


const HomeStyle = makeStyles((theme) => ({
    content: {
        padding: '24px'
    },

    dashed: {
        fontWeight: 500,
        borderBottom: '2px dashed black'
    }
}));

export default HomeStyle;
