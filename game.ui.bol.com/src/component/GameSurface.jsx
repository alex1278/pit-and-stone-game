import React from 'react';
import { connect } from 'react-redux';

import { boardLayoutSelector, gameStarted } from '../selector';

import Card from './Card';
import Pit from './Pit';

import './GameSurface.css';

const PlayingBoard = ({layout}) => (
    <div className="flex-row">
        <Pit id={layout.player2.large.id}
            type={layout.player2.large.type}
            stones={layout.player2.large.stones} />

        <div className="pit-row flex-row">
            { layout.player2.small.map(pit =>
                <Pit key={pit.id} id={pit.id} type={pit.type} stones={pit.stones} />)}

            { layout.player1.small.map(pit =>
                <Pit key={pit.id} id={pit.id} type={pit.type} stones={pit.stones} />)}
        </div>

        <Pit id={layout.player1.large.id}
            type={layout.player1.large.type}
            stones={layout.player1.large.stones} />
    </div>
)

const GameSurface = ({layout, gameStarted}) => (
    <div className="game-surface">
        <Card>
            { !gameStarted
            ? <h2 className="thin">Waiting for player to join...</h2>
            : <PlayingBoard layout={layout} /> }
        </Card>
    </div>
)

const mapStateToProps = state => {
    return {
        gameStarted: gameStarted(state),
        layout: boardLayoutSelector(state)
    }
}

export default connect(mapStateToProps)(GameSurface);