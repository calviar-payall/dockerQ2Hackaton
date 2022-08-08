/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2022 jPOS Software SRL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.Serializable;

import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.space.LocalSpace;
import org.jpos.space.SpaceSource;
import org.jpos.transaction.Context;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.TransactionParticipant;
import org.jpos.transaction.TransactionManager;

import static org.jpos.transaction.ContextConstants.*;

@SuppressWarnings("unused")
public class ParticipanteCesar implements TransactionParticipant {
    private String source;
    private String request;
    private String response;
    private LocalSpace isp;
    private long timeout = 70000L;

    

    public int prepare (long id, Serializable context) {
        Context ctx = (Context) context;
        ISOSource source = (ISOSource) ctx.get (this.source);
        return PREPARED | READONLY;
    }
    public void commit (long id, Serializable context) {
        Context ctx = (Context) context;
        ISOSource src = (ISOSource) ctx.get (source);
        ISOMsg m = (ISOMsg) ctx.get("REQUEST");
        try {
            m.setResponseMTI();

			m.set(37, "13"); // Código de enviado 1
            m.set(36, "Hola"); // Código de enviado 2
			m.set(34, "Mundo"); // Código de enviado 3
            ctx.put("RESPONSE",m);
        }
        catch (Throwable t) {
            ctx.log("lmao");
        }
    }
}