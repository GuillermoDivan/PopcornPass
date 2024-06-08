import HeaderDetail from "../molecules/MovieDetailHeader"
import { Box } from "@mui/material"
import MovieImageDetail from "../atoms/MovieImageDetail"
import MovieDetailContent from "../molecules/MovieDetailContent"
import { FC } from "react"
import { IMovie } from "@/common/interfaces"
import MovieDescriptionDetail from "../atoms/MovieDescriptionDetail"

const AVERAGE = 4.5

const MovieDetail: FC<{ movie: IMovie }> = ({ movie }) => {
    console.log(movie)
    return (<>
        <Box margin="1.5rem auto">
            <Box sx={{
                width: { xs: "93%", sm: "96%", md: "90%" }, margin: "1rem auto", borderRadius: "16px",
                display: "flex", bgcolor: { sm: "var(--lightBlack)" },
                flexDirection: { xs: "column", sm: "row" }, transition: "width ease .3s"
            }}>
                <MovieImageDetail imagePath={movie?.image} />

                <Box width={{ xs: "100%", sm: "57%", md: "calc(100% - 290px)" }} display="flex"
                    gap={{ xs: "1rem", sm: "0" }} flexDirection={"column"}
                    justifyContent={{ md: "space-evenly", sm: "space-between" }}>

                    <HeaderDetail
                        releaseDate={movie.releaseDate || "26.7.2024"}
                        title={movie.title || "Deadpool And Wolverine"}
                        runtime="1h30m"
                        threeD={movie.threeD ? "3D" : false}
                        vote_average={AVERAGE}
                        vote_count={1.612}
                    />

                    <MovieDetailContent
                        genre={movie.genre || ["acción", "comedia"]}
                        censorship={undefined}
                        certification={movie.adult ? "+18" : "ATP"}
                        original_language={movie.subtitle ? "INGLÉS" : "ESPAÑOL"}
                    />

                    <MovieDescriptionDetail
                        description={movie.description || "Lorem ipsum dolor sit amet consectetur adipisicing elit. Deserunt maiores ut libero amet porro, excepturi minus? Ut et provident cumque fugiat, hic aliquam commodi qui eos cupiditate corrupti quaerat eaque."}
                    />

                </Box>
            </Box>
        </Box>


    </>)
}

export default MovieDetail

/* 
ME FALTA OBTENER:
    -original_language
    -censorship || certification
    -runtime
    -vote_average
    -vote_count

      .url("https://api.themoviedb.org/3/movie/{movie_id}/release_dates")

*/