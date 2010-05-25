/**
 * Copyright (C) 2010 Osman Shoukry
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.openpojo.log;

import java.util.logging.Level;

import com.openpojo.business.BusinessIdentity;

/**
 * Our own JavaLogger adapter.<br>
 * The Mapping is done as follows:<br>
 * - Trace = Level.FINEST<br>
 * - Debug = Level.FINER<br>
 * - Info = Level.FINE<br>
 * - Warn = Level.WARNING<br>
 * - Error = Level.WARNING<br>
 * - Fatal = Level.SEVERE<br>
 */
final class JavaLogger extends Logger {

    private final java.util.logging.Logger logger;

    private JavaLogger(final String category) {
        logger = java.util.logging.Logger.getLogger(category);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isLoggable(Level.FINEST);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINER);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.FINE);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public void trace(final Object message) {
        logger.finest(toString(message));
    }

    @Override
    public void debug(final Object message) {
        logger.finer(toString(message));
    }

    @Override
    public void info(final Object message) {
        logger.fine(toString(message));
    }

    @Override
    public void warn(final Object message) {
        logger.warning(toString(message));
    }

    @Override
    public void error(final Object message) {
        // JavaLogging doesn't have error level, so we'll treat it as warning.
        warn(message);
    }

    @Override
    public void fatal(final Object message) {
        logger.severe(toString(message));
    }

    private String toString(final Object message) {
        return message == null ? null : message.toString();
    }

    @Override
    public String toString() {
        return BusinessIdentity.toString(this);
    }

}
