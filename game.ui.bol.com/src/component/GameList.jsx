import React from 'react';
import { connect } from 'react-redux';

import Card from './Card';
import GameListItem from './GameListItem';

import { GameActions } from '../state';

import './GameList.css'

const GameList = ({games, createGame}) => (
    <div className="game-list">
        <Card>
            <div className="flex-row content-spaced">
                <h1 className="thin">Game List</h1>
                <button onClick={createGame} className="blue">Create</button>
            </div>
            <div className="flex-column list">
            { games.length < 1
                ? <h3>There are no active games</h3>
                : games.map((game, index) => <GameListItem key={game.id} game={game} number={index + 1} />)
            }
            </div>
        </Card>
    </div>
)

const mapStateToProps = state => {
    return {
        games: state.games
    }
}

const mapDispatchToProps = dispatch => {
    return {
        createGame: () => dispatch(GameActions.createGame())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(GameList);