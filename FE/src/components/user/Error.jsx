import PropType from 'prop-types';
const ErrorCom = ({messageProps}) => {
    return(
        
            <section className="error__area pt-60 pb-100">
                <div className="container">
                    <div className="col-xl-8 offset-xl-2 col-lg-8 offset-lg-2">
                        <div className="error__content text-center">
                            <div className="error__number">
                                <h1>{messageProps.statusCode}</h1>
                            </div>
                            <span>component not found</span>
                            <h2>{messageProps.message}</h2>
                            <p>The page are looking for has been moved or doesn’t exist anymore, if you like you can return to our homepage. If the problem persists, please send us an email to <span className="highlight comment"><a href="/cdn-cgi/l/email-protection" className="__cf_email__" data-cfemail="7c1e1d0f151f08141911193c1b111d1510521f1311">[email&#160;protected]</a></span></p>
                        </div>
                    </div>
                </div>
            </section>
        
    )
}
ErrorCom.propTypes = {
    messageProps : PropType.object.isRequired
};export default ErrorCom