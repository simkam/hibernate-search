/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.search.elasticsearch.processor.impl;

import org.hibernate.search.backend.IndexingMonitor;
import org.hibernate.search.elasticsearch.client.impl.JestClient;
import org.hibernate.search.elasticsearch.impl.JestAPIFormatter;
import org.hibernate.search.elasticsearch.work.impl.ElasticsearchWorkExecutionContext;
import org.hibernate.search.exception.AssertionFailure;


/**
/**
 * The execution context used in {@link ElasticsearchWorkProcessor}
 * when multiple works are executed in parallel.
 * <p>
 * This context is immutable and thread-safe, but doesn't support
 * {@link #getBufferedIndexingMonitor(IndexingMonitor)} nor {@link #setIndexDirty(String)}.
 *
 * @author Yoann Rodiere
 */
class ParallelWorkExecutionContext implements ElasticsearchWorkExecutionContext {

	private final JestClient client;
	private final JestAPIFormatter jestAPIFormatter;

	public ParallelWorkExecutionContext(JestClient client, JestAPIFormatter jestAPIFormatter) {
		super();
		this.client = client;
		this.jestAPIFormatter = jestAPIFormatter;
	}

	@Override
	public JestClient getClient() {
		return client;
	}

	@Override
	public JestAPIFormatter getJestAPIFormatter() {
		return jestAPIFormatter;
	}

	@Override
	public void setIndexDirty(String indexName) {
		throw new AssertionFailure( "Unexpected dirty index with a default context."
				+ " Works that may alter index content should be executed"
				+ " through the BackendRequestProcessor, using an appropriate context." );
	}

	@Override
	public IndexingMonitor getBufferedIndexingMonitor(IndexingMonitor indexingMonitor) {
		throw new AssertionFailure( "Unexpected indexing monitor request with a default context."
				+ " Works that may alter index content should be executed"
				+ " through the BackendRequestProcessor, using an appropriate context." );
	}

}