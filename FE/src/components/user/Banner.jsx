
import sliderImage from '/img/slider/slider-1.jpg';
import PropTypes from "prop-types";


const Banner = ({pageName}) => {
    return (
        <section
        className="page__title p-relative d-flex align-items-center"
        style={{ backgroundImage: `url(${sliderImage})`}}
      >
        <div className="container">
          <div className="row">
            <div className="col-xl-12">
              <div className="page__title-inner text-center">
                <h1>{pageName}</h1>
                <div className="page__title-breadcrumb">
                  <nav aria-label="breadcrumb">
                    <ol className="breadcrumb justify-content-center">
                      <li className="breadcrumb-item">
                        <a href="index.html">Home</a>
                      </li>
                      <li className="breadcrumb-item active" aria-current="page">
                        {pageName}
                      </li>
                    </ol>
                  </nav>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

    )
}
export default Banner
Banner.propTypes = {
  pageName: PropTypes.node.isRequired,
};